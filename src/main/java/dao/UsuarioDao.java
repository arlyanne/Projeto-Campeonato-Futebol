package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;



import entities.Usuario;
import util.JPAUtil;

public class UsuarioDao {

	public static void save(Usuario usuario) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		em.persist(usuario);
		em.getTransaction().commit();
		em.close();
	}
	
	public static List<Usuario> listarTodos() {
		EntityManager em = JPAUtil.criarEntityManager();
		Query q = em.createQuery("select u from User u");
		List<Usuario> list = q.getResultList();
		em.close();
		return list;
	}
	
	 public static Usuario searchLogin(String login) {
	        EntityManager em = JPAUtil.criarEntityManager();
	        try {
	            String jpql = "select u from Usuario u where u.login = :login";
	            Query query = em.createQuery(jpql);
	            query.setParameter("login", login);
	            List<Usuario> result = query.getResultList();
	            
	            if (!result.isEmpty()) {
	                return result.get(0); 
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            em.close();
	        }
	        return null; 
	    }
	}

