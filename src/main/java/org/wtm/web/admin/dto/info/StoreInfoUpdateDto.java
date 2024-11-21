package org.wtm.web.admin.dto.info;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import jakarta.persistence.Embedded;
import lombok.*;

import java.time.LocalTime;
import java.util.List;
import org.wtm.web.auth.dto.Address;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreInfoUpdateDto {

    private String storeName;

    private List<String> snsAddress;
    private String phone;

    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonFormat(pattern = "HH:mm")
    private LocalTime openTime;

    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonFormat(pattern = "HH:mm")
    private LocalTime closeTime;

    // Address 관련 필드 추가
    private String postcode;
    private String address;
    private String detailAddress;
    private String extraAddress;

    public Address toAddress() {
        return new Address(postcode, address, detailAddress, extraAddress);
    }
}
