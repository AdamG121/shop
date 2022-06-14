package com.adam.shop.service.impl

import com.adam.shop.domain.dao.Basket
import com.adam.shop.domain.dao.Product
import com.adam.shop.domain.dao.User
import com.adam.shop.exception.ProductQuantityExceededException
import com.adam.shop.repository.BasketRepository
import com.adam.shop.service.ProductService
import com.adam.shop.service.UserService
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import spock.lang.Specification

class BasketServiceImplSpec extends Specification {
    def basketRepository = Mock(BasketRepository);
    def productService = Mock(ProductService);
    def userService = Mock(UserService);

    def basketServiceImpl = new BasketServiceImpl(basketRepository, productService, userService)

    def 'should add product'() {
        given:
        def productId = 1L
        def quantity = 10
        def user = new User()
        def product = Mock(Product)
        def basket = Basket.builder()
                .product(product)
                .user(user)
        //każda metoda wywołana na zmokowanym obiekcie zwróci null jesli wartość zwracana nie została zamokowana
                .quantity(quantity)
                .build()

        when:
        basketServiceImpl.addProduct(productId, quantity)

        then:
        1 * productService.getById(productId) >> product
        1 * product.getQuantity() >> quantity
        1 * userService.getCurrentUser() >> user
        1 * basketRepository.save(basket)
        0 * _

    }

    //jeśli testujemy metodę z exception, mokowania mogą być tylko w bloku given
    def 'should not add product'(){
        given:
        def productId = 1L
        def quantity = 10
        def product = new Product(quantity: 9)
        productService.getById(productId) >> product

        when:
        basketServiceImpl.addProduct(productId, quantity)

        //sprawdzenie czy wykonał się właściwy exception
        then:
        def e = thrown ProductQuantityExceededException
        e.message == 'Przekroczona dostępna ilość'
    }

    def 'should remove product'(){
        given:
        def productId = 1L
        def securityContext = Mock(SecurityContext)
        SecurityContextHolder.setContext(securityContext)
        def authentication = Mock(Authentication)

        when:
        basketServiceImpl.removeProduct(productId)

        then:
        1 * securityContext.getAuthentication() >> authentication
        1 * authentication.getName() >> 'admin@wp.pl'
        1 * basketRepository.deleteByProductIdAndUserEmail(productId, 'admin@wp.pl')
        0 * _
    }

    def 'should get basket'(){
        given:
        def securityContext = Mock(SecurityContext)
        SecurityContextHolder.setContext(securityContext)
        def authentication = Mock(Authentication)
        def basket1 = Mock(Basket)
        def basket2 = Mock(Basket)
        def basketList = [basket1, basket2]
        def product1 = Mock(Product)
        def product2 = Mock(Product)

        when:
        basketServiceImpl.getBasket()

        then:
        1 * securityContext.getAuthentication() >> authentication
        1 * authentication.getName() >> 'admin@wp.pl'
        1 * basketRepository.findByUserEmail('admin@wp.pl') >> basketList
        1 * basket1.getProduct() >> product1
        1 * basket1.getQuantity() >> 10
        1 * product1.setQuantity(10)
        1 * basket2.getProduct() >> product2
        1 * basket2.getQuantity() >> 20
        1 * product2.setQuantity(20)
        0 * _
    }

}
