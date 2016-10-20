package ua.com.daoImp;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ua.com.dao.ProductInterface;
import ua.com.entity.Product;

import javax.persistence.*;
import java.util.List;

/**
 * Created by devnull on 09.10.16.
 */
@Repository
public class ProductFunction implements ProductInterface{

    @PersistenceContext(name="primary")
    private EntityManager man;
    private final static String productTable = " Product ";


    @Transactional
    public void addProduct(Product product){
        man.persist(product);
    }

    @Transactional
    public List<Product> showAllProducts(){
        return man.createQuery("from"+productTable+"p").getResultList();
    }

    @Transactional
    public Product findProduct(String product_code){
        Product product;
            product = (Product)man.createQuery("select p from"+productTable+"p where p.product_code =:product_code").setParameter("product_code", product_code).getSingleResult();
        return product;
    }

    @Transactional
    public void deleteProduct(String product_code){
        Product product;
            product = (Product)man.createQuery("select p from"+productTable+"p where p.product_code =:product_code").setParameter("product_code", product_code).getSingleResult();
        if(product != null){
            man.remove(product);
        }
    }

    @Transactional
    public void sellProduct(String product_code, int number){
        Product product = null;
        try{
            product = (Product)man.createQuery("select p from"+productTable+"p where p.product_code =:product_code").setParameter("product_code", product_code).getSingleResult();
        }catch(NoResultException e){
            System.out.println("Не найден товар с данным кодом");
        }
        product.setProduct_number(""+(Integer.parseInt(product.getProduct_number())-number));
        man.merge(product);
    }
}