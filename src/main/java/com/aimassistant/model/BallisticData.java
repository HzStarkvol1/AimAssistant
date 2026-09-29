package com.aimassistant.model;

public record BallisticData(
        double targetDistanceMeters,
        double windSpeedMps,
        double windDirectionDegrees,
        double bulletWeightGrains,
        double ballisticCoefficient,
        double muzzleVelocityMps,
        double zeroingDistanceMeters
) {}
