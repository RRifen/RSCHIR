package com.rschir.prac.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.rschir.prac.util.views.Views;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client {

    @Id
    @Column(name = "client_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Get.class)
    private long clientId;

    @Column(name = "name", nullable = false)
    @JsonView({Views.Post.class})
    private String name;

    @Column(name = "email", nullable = false)
    @JsonView({Views.Post.class})
    private String email;

    @Column(name = "login", nullable = false)
    @JsonView({Views.Post.class})
    private String login;

    @Column(name = "password", nullable = false)
    @JsonView({Views.Post.class})
    private String password;

}
