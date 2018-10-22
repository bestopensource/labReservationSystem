/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.schema.ejb;

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;


public abstract class AbstractSessionSynchronization {
       
    protected static final Logger LOGGER = Logger.getGlobal();

    @Resource
    protected SessionContext sctx;
    
    private String transactionId;

    // Przywróć "wykomentowane" linie aby uzyskać prostą implementację SessionSynchronization
    public void afterBegin() throws EJBException {
        transactionId = Long.toString(System.currentTimeMillis())
                + ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
        LOGGER.log(Level.INFO, "Transakcja TXid={0} rozpoczęta w {1}, tożsamość: {2}",
                new Object[]{transactionId, this.getClass().getName(), sctx.getCallerPrincipal().getName()});
    }

    public void beforeCompletion() throws EJBException {
        LOGGER.log(Level.INFO, "Transakcja TXid={0} przed zatwierdzeniem w {1}, tożsamość {2}",
                new Object[]{transactionId, this.getClass().getName(), sctx.getCallerPrincipal().getName()});
    }

    public void afterCompletion(boolean committed) throws EJBException {
        LOGGER.log(Level.INFO, "Transakcja TXid={0} zakończona w {1} poprzez {3}, tożsamość {2}", 
                new Object[]{transactionId, this.getClass().getName(), sctx.getCallerPrincipal().getName(),
                    committed ? "ZATWIERDZENIE" : "ODWOŁANIE"});
    }
}
