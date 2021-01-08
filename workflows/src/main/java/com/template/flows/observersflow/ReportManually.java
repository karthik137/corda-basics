package com.template.flows.observersflow;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.FlowException;
import net.corda.core.flows.FlowLogic;
import net.corda.core.flows.FlowSession;
import net.corda.core.flows.InitiatingFlow;
import net.corda.core.identity.Party;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.utilities.ProgressTracker;

@InitiatingFlow
public class ReportManually extends FlowLogic<Void> {

    private final ProgressTracker progressTracker = new ProgressTracker();
    private final SignedTransaction signedTransaction;
    private final Party nationalRegulator;

    public ReportManually(SignedTransaction signedTransaction, Party nationalRegulator) {
        this.signedTransaction = signedTransaction;
        this.nationalRegulator = nationalRegulator;
    }

    @Override
    @Suspendable
    public Void call() throws FlowException {
        FlowSession flowSession = initiateFlow(nationalRegulator);
        flowSession.send(signedTransaction);
        return null;
    }
}
