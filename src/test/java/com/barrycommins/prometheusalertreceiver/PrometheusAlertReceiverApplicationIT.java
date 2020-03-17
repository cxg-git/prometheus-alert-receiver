package com.barrycommins.prometheusalertreceiver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AlertController.class)
public class PrometheusAlertReceiverApplicationIT {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AlertForwarder alertForwarder;

    @Test
    public void messageForwarded() throws Exception {

        String message = "{\n" +
                "\"receiver\": \"test_webhook\",\n" +
                "\"status\": \"firing\",\n" +
                "\"alerts\": [\n" +
                "{\n" +
                "\"status\": \"firing\",\n" +
                "\"labels\": {\n" +
                "\"alertname\": \"DiskUsage\",\n" +
                "\"severity\": \"critical\"\n" +
                "},\n" +
                "\"annotations\": {\n" +
                "\"description\": \"disk usage 95%\",\n" +
                "\"summary\": \"Disk usage critical\"\n" +
                "},\n" +
                "\"startsAt\": \"2018-10-02T10:00:00.105Z\",\n" +
                "\"endsAt\": \"2018-10-02T11:59:50.999Z\",\n" +
                "\"generatorURL\": \"alertmanager.localhost\"\n" +
                "}\n" +
                "],\n" +
                "\"externalURL\": \"alertmanager.localhost\",\n" +
                "\"version\": \"4\"\n" +
                "}";

        mockMvc.perform(post("/alerts").content(message).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(alertForwarder).send(any(PrometheusMessage.class));
    }

}
