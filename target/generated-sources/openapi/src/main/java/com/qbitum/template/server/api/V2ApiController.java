package com.qbitum.template.server.api;

import com.qbitum.template.server.model.SampleResponse;
import com.qbitum.template.server.model.ServerErrorResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-09T12:57:32.932654179+05:30[Asia/Colombo]")
@Controller
@RequestMapping("${openapi.templateSample.base-path:/api}")
public class V2ApiController implements V2Api {

    private final V2ApiDelegate delegate;

    public V2ApiController(@Autowired(required = false) V2ApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new V2ApiDelegate() {});
    }

    @Override
    public V2ApiDelegate getDelegate() {
        return delegate;
    }

}
