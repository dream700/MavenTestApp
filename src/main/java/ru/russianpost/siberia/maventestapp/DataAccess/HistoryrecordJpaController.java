/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia.maventestapp.DataAccess;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ru.russianpost.siberia.maventestapp.DataAccess.exceptions.NonexistentEntityException;
import ru.russianpost.siberia.maventestapp.DataAccess.exceptions.PreexistingEntityException;

/**
 *
 * @author Andrey.Isakov
 */
public class HistoryrecordJpaController implements Serializable {

    public HistoryrecordJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Historyrecord historyrecord) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ticket barcode = historyrecord.getBarcode();
            if (barcode != null) {
                barcode = em.getReference(barcode.getClass(), barcode.getBarcode());
                historyrecord.setBarcode(barcode);
            }
            em.persist(historyrecord);
            if (barcode != null) {
                barcode.getHistoryrecordCollection().add(historyrecord);
                barcode = em.merge(barcode);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHistoryrecord(historyrecord.getId()) != null) {
                throw new PreexistingEntityException("Historyrecord " + historyrecord + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Historyrecord historyrecord) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Historyrecord persistentHistoryrecord = em.find(Historyrecord.class, historyrecord.getId());
            Ticket barcodeOld = persistentHistoryrecord.getBarcode();
            Ticket barcodeNew = historyrecord.getBarcode();
            if (barcodeNew != null) {
                barcodeNew = em.getReference(barcodeNew.getClass(), barcodeNew.getBarcode());
                historyrecord.setBarcode(barcodeNew);
            }
            historyrecord = em.merge(historyrecord);
            if (barcodeOld != null && !barcodeOld.equals(barcodeNew)) {
                barcodeOld.getHistoryrecordCollection().remove(historyrecord);
                barcodeOld = em.merge(barcodeOld);
            }
            if (barcodeNew != null && !barcodeNew.equals(barcodeOld)) {
                barcodeNew.getHistoryrecordCollection().add(historyrecord);
                barcodeNew = em.merge(barcodeNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = historyrecord.getId();
                if (findHistoryrecord(id) == null) {
                    throw new NonexistentEntityException("The historyrecord with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Historyrecord historyrecord;
            try {
                historyrecord = em.getReference(Historyrecord.class, id);
                historyrecord.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historyrecord with id " + id + " no longer exists.", enfe);
            }
            Ticket barcode = historyrecord.getBarcode();
            if (barcode != null) {
                barcode.getHistoryrecordCollection().remove(historyrecord);
                barcode = em.merge(barcode);
            }
            em.remove(historyrecord);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Historyrecord> findHistoryrecordEntities() {
        return findHistoryrecordEntities(true, -1, -1);
    }

    public List<Historyrecord> findHistoryrecordEntities(int maxResults, int firstResult) {
        return findHistoryrecordEntities(false, maxResults, firstResult);
    }

    private List<Historyrecord> findHistoryrecordEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Historyrecord.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Historyrecord findHistoryrecord(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Historyrecord.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistoryrecordCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Historyrecord> rt = cq.from(Historyrecord.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
