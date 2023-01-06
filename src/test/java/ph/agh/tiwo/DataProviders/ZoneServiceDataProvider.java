//package ph.agh.tiwo.DataProviders;
//
//import engineering.thesis.PSR.Entities.ZoneEntity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ZoneServiceDataProvider {
//
//    public static final Long zoneId = (long)1;
//    public static ZoneEntity zonesEntity;
//    public static ZoneEntity zonesEntity1;
//    public static ZoneEntity zonesEntity2;
//    public static ZoneEntity zonesEntity3;
//    public static List<ZoneEntity> zonesEntities;
//    static {
//        zonesEntity = ZoneEntity.builder().zoneId(zoneId).city("test_city").CordX(1).CordY(1).occupiedRatio(0.5).attractivenessRatio(0.5).build();
//        zonesEntity1 = ZoneEntity.builder().zoneId(zoneId).city("new_city").CordX(2).CordY(2).occupiedRatio(0.3).attractivenessRatio(0.3).build();
//        zonesEntity2 = ZoneEntity.builder().zoneId(2L).city("test_city").CordX(1).CordY(1).occupiedRatio(0.5).attractivenessRatio(0.5).build();
//        zonesEntity3 = ZoneEntity.builder().zoneId(2L).city("test_city").CordX(1).CordY(1).occupiedRatio(1.0).attractivenessRatio(1.0).build();
//        zonesEntities = new ArrayList<>();
//        zonesEntities.add(zonesEntity);
//        zonesEntities.add(zonesEntity1);
//    }
//}
