package com.example.board.validation;


import com.example.board.domain.Post;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BeanValidation {

    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    @Test
    void messageCodesResolverObject() {
        String[] messageCodes = codesResolver.resolveMessageCodes("NotNull", "post");
        assertThat(messageCodes).containsExactly("NotNull.post", "NotNull");
    }

    @Test
    void messageCodesResolverField() {
        String[] messageCodes = codesResolver.resolveMessageCodes("NotNull", "post", "title", String.class);
        assertThat(messageCodes).containsExactly(
                "NotNull.post.title",
                "NotNull.title",
                "NotNull.java.lang.String",
                "NotNull"
        );
    }



    @Test
    @DisplayName("Post Entity Validation")
    public void postEntityBeanValidation() throws Exception {
        //given
        ValidatorFactory validation = Validation.buildDefaultValidatorFactory();
        Validator validator = validation.getValidator();

        Post post = new Post("", "");

        //when
        Set<ConstraintViolation<Post>> validate = validator.validate(post);
        for (ConstraintViolation<Post> postConstraintViolation : validate) {
            System.out.println("postConstraintViolation = " + postConstraintViolation);
            System.out.println("postConstraintViolation.getMessage() = " + postConstraintViolation.getMessage());
//            System.out.println("postConstraintViolation.getInvalidValue() = " + postConstraintViolation.getInvalidValue());
        }

    }
}
