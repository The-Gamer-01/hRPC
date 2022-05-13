package com.hyx;

import lombok.*;

/**
 * @author 黄乙轩
 * @version 1.0
 * @className com.hyx.Hello
 * @description TODO
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
