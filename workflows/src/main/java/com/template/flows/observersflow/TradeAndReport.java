package com.template.flows.observersflow;

import co.paralleluniverse.fibers.Suspendable;
import com.template.contracts.HighlyRegulatedContract;
import com.template.states.HighlyRegulatedState;
import net.corda.core.flows.*;
import net.corda.core.identity.Party;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.transactions.TransactionBuilder;

import java.util.Arrays;
import java.util.List;

@InitiatingFlow
@StartableByRPC
public class TradeAndReport extends FlowLogic<Void> {

    private final Party buyer;
    private final Party stateRegulator;
    private final Party nationalRegulator;

    public TradeAndReport(Party buyer, Party stateRegulator, Party nationalRegulator) {
        this.buyer = buyer;
        this.stateRegulator = stateRegulator;
        this.nationalRegulator = nationalRegulator;
    }

    @Override
    @Suspendable
    public Void call() throws FlowException {

        final Party notary = getServiceHub().getNetworkMapCache().getNotaryIdentities().get(0);

        //Create state
        HighlyRegulatedState highlyRegulatedState = new HighlyRegulatedState(buyer,getOurIdentity());

        TransactionBuilder transactionBuilder = new TransactionBuilder(notary)
                .addOutputState(highlyRegulatedState)
                .addCommand(new HighlyRegulatedContract.Commands.Trade(), getOurIdentity().getOwningKey());

        //Sign the transaction
        SignedTransaction signedTransaction = getServiceHub().signInitialTransaction(transactionBuilder);

        //Setup session with parties
        List<FlowSession> flowSession = Arrays.asList(initiateFlow(buyer), initiateFlow(stateRegulator));

        //Distribute the transaction using FinalityFlow

        subFlow(new FinalityFlow(signedTransaction, flowSession));


        //Also distribute the transaction to the national regulator manually.
        subFlow(new ReportManually(signedTransaction, nationalRegulator));
        return null;
    }


}
