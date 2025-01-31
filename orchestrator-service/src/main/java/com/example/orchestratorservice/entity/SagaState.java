package com.example.orchestratorservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "saga_states")
public class SagaState extends BaseEntity {

    @Id
    public String id;

    public String step;

    @Column(columnDefinition = "TEXT")
    public String payload;

    @Override
    public String toString() {
        return "SagaState{" + "id='" + id + '\'' + ", step='" + step + '\'' + ", payload='" + payload + '\'' + '}';
    }
}
