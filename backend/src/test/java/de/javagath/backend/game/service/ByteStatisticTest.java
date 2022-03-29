package de.javagath.backend.game.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import de.javagath.backend.game.model.enums.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.boot.test.context.SpringBootTest;

@SuppressWarnings("javadoc")
@SpringBootTest
public class ByteStatisticTest {

  ByteStatistic newByteStatistic;

  @BeforeEach
  void beforeEach() {
    newByteStatistic = ByteStatistic.newInstance(Owner.BOT, Owner.PLAYER);
  }

  @Test
  void isBytePenalty_newByteStatisticTest_ReturnsFalse() {

    assertThat(newByteStatistic.isBytePenalty()).isFalse();
  }

  @Test
  void isBytePenalty_byteStatisticBotGets2Bytes_ReturnsFalse() {

    newByteStatistic.addByte(Owner.BOT, 1);
    newByteStatistic.addByte(Owner.BOT, 2);

    assertThat(newByteStatistic.isBytePenalty()).isFalse();
  }

  @Test
  void isBytePenalty_newByteStatisticTestPlayerGets3Bytes_ReturnsTrue() {

    newByteStatistic.addByte(Owner.PLAYER, 1);
    newByteStatistic.addByte(Owner.PLAYER, 2);
    newByteStatistic.addByte(Owner.PLAYER, 3);

    assertThat(newByteStatistic.isBytePenalty()).isTrue();
  }

  @Test
  void getPenaltyOwner_ownerHas3BytesPlaysBytePenalty_ReturnsNobody() {
    newByteStatistic.addByte(Owner.PLAYER, 1);
    newByteStatistic.addByte(Owner.PLAYER, 2);
    newByteStatistic.addByte(Owner.PLAYER, 3);

    newByteStatistic.playBytePenalty(Owner.PLAYER);

    assertThat(newByteStatistic.getBytePenaltyOwner()).isEqualTo(Owner.NOBODY);
  }

  @ParameterizedTest
  @EnumSource(
      value = Owner.class,
      names = {"BOT", "PLAYER"})
  void getPenaltyOwner_ownerHas3Bytes_ReturnsOwner(Owner owner) {

    newByteStatistic.addByte(owner, 1);
    newByteStatistic.addByte(owner, 2);
    newByteStatistic.addByte(owner, 3);

    assertThat(newByteStatistic.getBytePenaltyOwner()).isEqualTo(owner);
  }
}
