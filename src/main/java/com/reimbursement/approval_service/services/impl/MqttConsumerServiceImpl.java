package com.reimbursement.approval_service.services.impl;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reimbursement.approval_service.dtos.ApprovalDto;
import com.reimbursement.approval_service.services.ApprovalService;

import jakarta.annotation.PostConstruct;

@Service
public class MqttConsumerServiceImpl {

    private final MqttClient mqttClient;
    private final ApprovalService service;

    public MqttConsumerServiceImpl(MqttClient mqttClient, ApprovalService service) {
        this.mqttClient = mqttClient;
        this.service = service;
    }

    @PostConstruct
    public void subscribe() throws MqttException {
        mqttClient.subscribe("topic/approval", (topic, message) -> {
            ObjectMapper mapper = new ObjectMapper();
            ApprovalDto approval = mapper.readValue(message.getPayload(), ApprovalDto.class);
            System.out.println(approval);
        });
    }

}
