package com.template.flows.observersflow;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.FlowException;
import net.corda.core.flows.FlowLogic;
import net.corda.core.flows.FlowSession;
import net.corda.core.flows.InitiatedBy;
import net.corda.core.node.StatesToRecord;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.utilities.UntrustworthyData;

import java.util.Arrays;

@InitiatedBy(ReportManually.class)
public class ReportManuallyResponder extends FlowLogic<Void> {
    private final FlowSession counterPartySession;

    public ReportManuallyResponder(FlowSession counterPartySession) {
        this.counterPartySession = counterPartySession;
    }

    @Override
    @Suspendable
    public Void call() throws FlowException {
        SignedTransaction signedTransaction = counterPartySession.receive(SignedTransaction.class).unwrap(data -> data);

        getServiceHub().recordTransactions(StatesToRecord.ALL_VISIBLE, Arrays.asList(signedTransaction));
        return null;
    }
}
