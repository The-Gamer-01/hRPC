package com.hyx;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Hello 实体类.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/5/10 10:59
 **/

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Hello {
    private String message;
    
    private String description;
}
