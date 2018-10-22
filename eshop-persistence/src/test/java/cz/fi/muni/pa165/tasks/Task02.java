package cz.fi.muni.pa165.tasks;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Product;

@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class Task02 extends AbstractTestNGSpringContextTests {

	@PersistenceUnit
	private EntityManagerFactory emf;

	private Category electro;
	private Category kitchen;

	private Product product1;
	private Product product2;
	private Product product3;

	@BeforeClass
	public void initTests() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Category electroCat = new Category();
		electroCat.setName("Electro");
		em.persist(electroCat);

		Category kitchenCat = new Category();
		kitchenCat.setName("Kitchen");
		em.persist(kitchenCat);

		Product prod1 = new Product();
		prod1.setName("Plate");
		prod1.addCategory(kitchenCat);
		em.persist(prod1);

		Product prod2 = new Product();
		prod2.setName("Flashlight");
		prod2.addCategory(electroCat);
		em.persist(prod2);

		Product prod3 = new Product();
		prod3.setName("Kitchen robot");
		prod3.addCategory(kitchenCat);
		prod3.addCategory(electroCat);
		em.persist(prod3);

		em.getTransaction().commit();
		em.close();

		electro = electroCat;
		kitchen = kitchenCat;
		product1 = prod1;
		product2 = prod2;
		product3 = prod3;
	}

	@Test
	public void electroTest() {
		EntityManager em = emf.createEntityManager();
		Category cat = em.find(Category.class, electro.getId());

		em.close();

		assertContainsProductWithName(cat.getProducts(), "Flashlight");
		assertContainsProductWithName(cat.getProducts(), "Kitchen robot");
	}

	@Test
	public void kitchenTest() {
		EntityManager em = emf.createEntityManager();
		Category cat = em.find(Category.class, kitchen.getId());

		em.close();

		assertContainsProductWithName(cat.getProducts(), "Plate");
		assertContainsProductWithName(cat.getProducts(), "Kitchen robot");
	}

	@Test
	public void plateTest() {
		EntityManager em = emf.createEntityManager();
		Product prod = em.find(Product.class, product1.getId());

		em.close();

		assertContainsCategoryWithName(prod.getCategories(), "Kitchen");
	}

	@Test
	public void flashTest() {
		EntityManager em = emf.createEntityManager();
		Product prod = em.find(Product.class, product2.getId());

		em.close();

		assertContainsCategoryWithName(prod.getCategories(), "Electro");
	}

	@Test
	public void robotTest() {
		EntityManager em = emf.createEntityManager();
		Product prod = em.find(Product.class, product3.getId());

		em.close();

		assertContainsCategoryWithName(prod.getCategories(), "Kitchen");
		assertContainsCategoryWithName(prod.getCategories(), "Electro");
	}


	private void assertContainsCategoryWithName(Set<Category> categories,
			String expectedCategoryName) {
		for(Category cat: categories){
			if (cat.getName().equals(expectedCategoryName))
				return;
		}
			
		Assert.fail("Couldn't find category "+ expectedCategoryName+ " in collection "+categories);
	}
	private void assertContainsProductWithName(Set<Product> products,
			String expectedProductName) {
		
		for(Product prod: products){
			if (prod.getName().equals(expectedProductName))
				return;
		}
			
		Assert.fail("Couldn't find product "+ expectedProductName+ " in collection "+products);
	}

	
}
