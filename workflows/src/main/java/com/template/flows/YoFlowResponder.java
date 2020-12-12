package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.*;
import net.corda.core.transactions.SignedTransaction;

@InitiatedBy(SendYo.class)
public class YoFlowResponder extends FlowLogic<SignedTransaction> {

    private final FlowSession counterPartySession;

    public YoFlowResponder(FlowSession counterPartySession) {
        this.counterPartySession = counterPartySession;
    }

    @Override
    @Suspendable
    public SignedTransaction call() throws FlowException {

        return subFlow(new ReceiveFinalityFlow(counterPartySession));

    }
}
