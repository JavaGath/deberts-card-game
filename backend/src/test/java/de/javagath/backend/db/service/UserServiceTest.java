package de.javagath.backend.db.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

import de.javagath.backend.db.model.UserEntity;
import java.lang.invoke.MethodHandles;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-unittest.properties")
@Sql(scripts = "/create-test-data.sql")
@Sql(scripts = "/cleanup-test-data.sql", executionPhase = AFTER_TEST_METHOD)
public class UserServiceTest {

  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

  @Autowired private UserService userService;

  @Test
  void selectUserByEmail_selectExistedUser_selectExpectedUserEntity() {
    UserEntity expectedUserEntity = new UserEntity();
    expectedUserEntity.setId(1);
    expectedUserEntity.setName("Plitochnik");
    expectedUserEntity.setEmail("Plitochnik@gmail.com");
    expectedUserEntity.setSalt("LExkyZ/GnDOAnntwOfrkwO");
    expectedUserEntity.setPassword("Q2ti1YG5isZh9aN8csiy..KpCqt3v6Vbr3HDQ3Chojc1IdqdhNAgm");
    expectedUserEntity.setLastGameResult(null);
    expectedUserEntity.setTotalWins(0);
    expectedUserEntity.setTotalLoses(0);
    expectedUserEntity.setWinRate(0);
    expectedUserEntity.setActualWinStreak(0);
    expectedUserEntity.setBestWinSteak(0);

    UserEntity selectedEntity = userService.selectUserByEmail(expectedUserEntity.getEmail());
    LOG.info(selectedEntity.toString());

    assertThat(expectedUserEntity.getId()).isEqualTo(selectedEntity.getId());
    assertThat(expectedUserEntity.getName()).isEqualTo(selectedEntity.getName());
    assertThat(expectedUserEntity.getEmail()).isEqualTo(selectedEntity.getEmail());
    assertThat(expectedUserEntity.getSalt()).isEqualTo(selectedEntity.getSalt());
    assertThat(expectedUserEntity.getPassword()).isEqualTo(selectedEntity.getPassword());
    assertThat(expectedUserEntity.getLastGameResult())
        .isEqualTo(selectedEntity.getLastGameResult());
    assertThat(expectedUserEntity.getTotalWins()).isEqualTo(selectedEntity.getTotalWins());
    assertThat(expectedUserEntity.getTotalLoses()).isEqualTo(selectedEntity.getTotalLoses());
    assertThat(expectedUserEntity.getWinRate()).isEqualTo(selectedEntity.getWinRate());
    assertThat(expectedUserEntity.getActualWinStreak())
        .isEqualTo(selectedEntity.getActualWinStreak());
    assertThat(expectedUserEntity.getBestWinSteak()).isEqualTo(selectedEntity.getBestWinSteak());
    assertThat(expectedUserEntity).isEqualTo(selectedEntity);
    assertThat(expectedUserEntity.hashCode()).isEqualTo(selectedEntity.hashCode());
  }
}
