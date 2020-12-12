package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import com.template.contracts.YoContract;
import com.template.states.YoState;
import net.corda.core.contracts.Command;
import net.corda.core.contracts.StateAndContract;
import net.corda.core.flows.*;
import net.corda.core.identity.Party;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.transactions.TransactionBuilder;
import net.corda.core.utilities.ProgressTracker;

import java.util.Arrays;

@InitiatingFlow
@StartableByRPC
public class SendYo extends FlowLogic<SignedTransaction> {

    private final Party target;
    private final String yoMessage;

    ProgressTracker progressTracker = new ProgressTracker();


    public SendYo(Party target, String yoMessage) {
        this.target = target;
        this.yoMessage = yoMessage;
    }

    @Override
    @Suspendable
    public SignedTransaction call() throws FlowException {

        //Get my identity
        Party me = getOurIdentity();

        //Get notary
        Party notary = getServiceHub().getNetworkMapCache().getNotaryIdentities().get(0);

        //Get command
        Command<YoContract.Commands.Send> command = new Command<YoContract.Commands.Send>(new YoContract.Commands.Send(), Arrays.asList(me.getOwningKey()));

        //Create state
        YoState yoState = new YoState(me,target,yoMessage);

        StateAndContract stateAndContract = new StateAndContract(yoState, YoContract.ID);
        TransactionBuilder utx = new TransactionBuilder(notary).withItems(stateAndContract, command);

        utx.verify(getServiceHub());

        //Sign the transaction
        SignedTransaction signedTransaction = getServiceHub().signInitialTransaction(utx);

        //Initiate flow
        FlowSession flowSession = initiateFlow(target);

        //Return
        return subFlow(new FinalityFlow(signedTransaction, flowSession));
    }
}
