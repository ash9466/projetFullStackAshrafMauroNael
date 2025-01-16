package com.fullstack.projet.models;

import java.io.Serializable;

public interface ValidatableObject extends Serializable {
    void validate();
}
