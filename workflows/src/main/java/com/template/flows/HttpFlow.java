package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.FlowException;
import net.corda.core.flows.FlowLogic;
import net.corda.core.flows.InitiatingFlow;
import net.corda.core.flows.StartableByRPC;
import net.corda.core.utilities.ProgressTracker;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

@InitiatingFlow
@StartableByRPC
public class HttpFlow extends FlowLogic<String> {

    ProgressTracker progressTracker = new ProgressTracker();

    @Override
    @Suspendable
    public String call() throws FlowException {
        Request request = new Request.Builder().url(Constants.US_COVID_STATUS).build();
        String httpValue = null;
        JSONObject jsonObject = null;
        try{
            Response httpResponse = new OkHttpClient().newCall(request).execute();
            httpValue = httpResponse.body().string();

            //jsonObject = (JSONObject) new JSONParser().parse(httpValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpValue;
    }
}
