package com.academia.fit.controller.exercise;

import com.academia.fit.dto.exercise.ExerciseRequest;
import com.academia.fit.utils.enums.MuscleGroup;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ExerciseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateExerciseSuccessfully() throws Exception {
        ExerciseRequest request = new ExerciseRequest();
        request.setName("Supino Reto");
        request.setDescription("Exercício clássico de peitoral.");
        request.setPrimaryMuscleGroup(MuscleGroup.CHEST);

        mockMvc.perform(post("/api/exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Supino Reto"))
                .andExpect(jsonPath("$.primaryMuscleGroup").value("CHEST"));
    }
}
