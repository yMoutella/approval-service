package com.reimbursement.approval_service.services.impl;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.reimbursement.approval_service.dtos.ApprovalDto;
import com.reimbursement.approval_service.services.ApprovalService;
import com.reimbursement.approval_service.services.ExpenseTopicService;

import jakarta.annotation.PostConstruct;

@Service
public class ExpenseTopicServiceImpl implements ExpenseTopicService {

    private final MqttClient mqttClient;
    private final ApprovalService service;

    public ExpenseTopicServiceImpl(MqttClient mqttClient, ApprovalService service) {
        this.mqttClient = mqttClient;
        this.service = service;
    }

    @Override
    @PostConstruct
    public void subscribe() throws MqttException {
        mqttClient.subscribe("topic/approval", (topic, message) -> {
            ObjectMapper mapper = new ObjectMapper();
            ApprovalDto approval = mapper.readValue(message.getPayload(), ApprovalDto.class);
            String a = new String(message.getPayload());
            System.out.println("Incoming message: " + a);
            try {
                service.saveApproval(approval);
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        });
    }

    @Override
    public void publishMessage(ApprovalDto approval) throws MqttException {

        final String TOPIC = "topic/expenses";
        final Integer QOS = 2;

        Gson gson = new Gson();
        byte[] payload = gson.toJson(approval).getBytes();

        MqttMessage message = new MqttMessage();
        message.setQos(QOS);
        message.setPayload(payload);

        mqttClient.publish(TOPIC, message);

    }

}
