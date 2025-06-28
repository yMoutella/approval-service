package com.reimbursement.approval_service.services;

import org.eclipse.paho.client.mqttv3.MqttException;

import com.reimbursement.approval_service.dtos.ApprovalDto;

public interface ExpenseTopicService {

    void subscribe() throws MqttException;

    void publishMessage(ApprovalDto approval) throws MqttException;

}
