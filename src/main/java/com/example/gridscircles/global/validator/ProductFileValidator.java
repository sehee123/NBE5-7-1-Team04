package com.example.gridscircles.global.validator;

import com.example.gridscircles.global.annotation.ProductValidFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class ProductFileValidator implements ConstraintValidator<ProductValidFile, MultipartFile> {

    private long maxSize;

    @Override
    public void initialize(ProductValidFile constraintAnnotation) {
        this.maxSize = constraintAnnotation.maxSize();
    }

    @Override
    public boolean isValid(MultipartFile file,
        ConstraintValidatorContext context) {

        if (file == null || file.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("파일은 필수입니다.").addConstraintViolation();
            return false;
        }

        if (file.getSize() > maxSize) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("파일은 최대 " + (maxSize / 1024 / 1024) + "MB까지 업로드 가능합니다.").addConstraintViolation();
            return false;
        }

        return true;
    }
}
