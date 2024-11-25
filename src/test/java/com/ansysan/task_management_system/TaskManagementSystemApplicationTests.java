package com.ansysan.task_management_system;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TaskManagementSystemApplicationTests {

    @Test
    void contextLoads() {
        Assertions.assertThat(40 * 2).isEqualTo(80);
    }

}
