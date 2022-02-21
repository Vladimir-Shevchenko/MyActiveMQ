package com.example.MyActiveMQ;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Email {

    private UUID correlationID;
    private int firstNum;
    private int secondNum;


}