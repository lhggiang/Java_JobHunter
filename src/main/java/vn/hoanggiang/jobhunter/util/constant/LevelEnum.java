package vn.hoanggiang.jobhunter.util.constant;

public enum LevelEnum {
  INTERN, FRESHER, JUNIOR, MIDDLE, SENIOR;

  public static LevelEnum fromString(String level) {
    if (level == null) {
      throw new IllegalArgumentException("Level cannot be null");
    }
    try {
      return LevelEnum.valueOf(level.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid level: " + level);
    }
  }
}
