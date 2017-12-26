/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia.maventestapp.DataAccess;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import ru.russianpost.siberia.maventestapp.DataAccess.exceptions.NonexistentEntityException;
import ru.russianpost.siberia.maventestapp.DataAccess.exceptions.PreexistingEntityException;

/**
 *
 * @author Andrey.Isakov
 */
public class TicketJpaController implements Serializable {

    public TicketJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ticket ticket) throws PreexistingEntityException, Exception {
        if (ticket.getHistoryrecordCollection() == null) {
            ticket.setHistoryrecordCollection(new ArrayList<Historyrecord>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Historyrecord> attachedHistoryrecordCollection = new ArrayList<Historyrecord>();
            for (Historyrecord historyrecordCollectionHistoryrecordToAttach : ticket.getHistoryrecordCollection()) {
                historyrecordCollectionHistoryrecordToAttach = em.getReference(historyrecordCollectionHistoryrecordToAttach.getClass(), historyrecordCollectionHistoryrecordToAttach.getId());
                attachedHistoryrecordCollection.add(historyrecordCollectionHistoryrecordToAttach);
            }
            ticket.setHistoryrecordCollection(attachedHistoryrecordCollection);
            em.persist(ticket);
            for (Historyrecord historyrecordCollectionHistoryrecord : ticket.getHistoryrecordCollection()) {
                Ticket oldBarcodeOfHistoryrecordCollectionHistoryrecord = historyrecordCollectionHistoryrecord.getBarcode();
                historyrecordCollectionHistoryrecord.setBarcode(ticket);
                historyrecordCollectionHistoryrecord = em.merge(historyrecordCollectionHistoryrecord);
                if (oldBarcodeOfHistoryrecordCollectionHistoryrecord != null) {
                    oldBarcodeOfHistoryrecordCollectionHistoryrecord.getHistoryrecordCollection().remove(historyrecordCollectionHistoryrecord);
                    oldBarcodeOfHistoryrecordCollectionHistoryrecord = em.merge(oldBarcodeOfHistoryrecordCollectionHistoryrecord);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTicket(ticket.getBarcode()) != null) {
                throw new PreexistingEntityException("Ticket " + ticket + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ticket ticket) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ticket persistentTicket = em.find(Ticket.class, ticket.getBarcode());
            Collection<Historyrecord> historyrecordCollectionOld = persistentTicket.getHistoryrecordCollection();
            Collection<Historyrecord> historyrecordCollectionNew = ticket.getHistoryrecordCollection();
            Collection<Historyrecord> attachedHistoryrecordCollectionNew = new ArrayList<Historyrecord>();
            for (Historyrecord historyrecordCollectionNewHistoryrecordToAttach : historyrecordCollectionNew) {
                historyrecordCollectionNewHistoryrecordToAttach = em.getReference(historyrecordCollectionNewHistoryrecordToAttach.getClass(), historyrecordCollectionNewHistoryrecordToAttach.getId());
                attachedHistoryrecordCollectionNew.add(historyrecordCollectionNewHistoryrecordToAttach);
            }
            historyrecordCollectionNew = attachedHistoryrecordCollectionNew;
            ticket.setHistoryrecordCollection(historyrecordCollectionNew);
            ticket = em.merge(ticket);
            for (Historyrecord historyrecordCollectionOldHistoryrecord : historyrecordCollectionOld) {
                if (!historyrecordCollectionNew.contains(historyrecordCollectionOldHistoryrecord)) {
                    historyrecordCollectionOldHistoryrecord.setBarcode(null);
                    historyrecordCollectionOldHistoryrecord = em.merge(historyrecordCollectionOldHistoryrecord);
                }
            }
            for (Historyrecord historyrecordCollectionNewHistoryrecord : historyrecordCollectionNew) {
                if (!historyrecordCollectionOld.contains(historyrecordCollectionNewHistoryrecord)) {
                    Ticket oldBarcodeOfHistoryrecordCollectionNewHistoryrecord = historyrecordCollectionNewHistoryrecord.getBarcode();
                    historyrecordCollectionNewHistoryrecord.setBarcode(ticket);
                    historyrecordCollectionNewHistoryrecord = em.merge(historyrecordCollectionNewHistoryrecord);
                    if (oldBarcodeOfHistoryrecordCollectionNewHistoryrecord != null && !oldBarcodeOfHistoryrecordCollectionNewHistoryrecord.equals(ticket)) {
                        oldBarcodeOfHistoryrecordCollectionNewHistoryrecord.getHistoryrecordCollection().remove(historyrecordCollectionNewHistoryrecord);
                        oldBarcodeOfHistoryrecordCollectionNewHistoryrecord = em.merge(oldBarcodeOfHistoryrecordCollectionNewHistoryrecord);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = ticket.getBarcode();
                if (findTicket(id) == null) {
                    throw new NonexistentEntityException("The ticket with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ticket ticket;
            try {
                ticket = em.getReference(Ticket.class, id);
                ticket.getBarcode();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ticket with id " + id + " no longer exists.", enfe);
            }
            Collection<Historyrecord> historyrecordCollection = ticket.getHistoryrecordCollection();
            for (Historyrecord historyrecordCollectionHistoryrecord : historyrecordCollection) {
                historyrecordCollectionHistoryrecord.setBarcode(null);
                historyrecordCollectionHistoryrecord = em.merge(historyrecordCollectionHistoryrecord);
            }
            em.remove(ticket);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ticket> findTicketEntities() {
        return findTicketEntities(true, -1, -1);
    }

    public List<Ticket> findTicketEntities(int maxResults, int firstResult) {
        return findTicketEntities(false, maxResults, firstResult);
    }

    private List<Ticket> findTicketEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ticket.class));
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

    public Ticket findTicket(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ticket.class, id);
        } finally {
            em.close();
        }
    }

    public int getTicketCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ticket> rt = cq.from(Ticket.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
