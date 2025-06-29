package com.reimbursement.approval_service.application.services;

import org.eclipse.paho.client.mqttv3.MqttException;

import com.reimbursement.approval_service.adapters.inbound.dtos.ApprovalDto;

public interface ExpenseTopicService {

    void subscribe() throws MqttException;

    void publishMessage(ApprovalDto approval) throws MqttException;

}
