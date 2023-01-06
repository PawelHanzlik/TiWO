//package ph.agh.tiwo.UnitTests;
//
//import engineering.thesis.PSR.Entities.ZoneEntity;
//import engineering.thesis.PSR.Exceptions.Classes.NoSuchZoneException;
//import engineering.thesis.PSR.Repositories.ZoneRepository;
//import engineering.thesis.PSR.Services.ZoneServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//
//import java.util.List;
//import java.util.Optional;
//
//import static engineering.thesis.PSR.DataProviders.ZoneServiceDataProvider.*;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.when;
//public class ZoneServiceUnitTests {
//
//    @InjectMocks
//    ZoneServiceImpl zoneService;
//
//    @Mock
//    ZoneRepository zoneRepository;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void getZoneTest(){
//        when(zoneRepository.findById(zoneId)).thenReturn(Optional.ofNullable(zonesEntity));
//        ZoneEntity getZoneEntity = this.zoneService.getZone(zoneId);
//        assertNotNull(getZoneEntity);
//        assertEquals(1, getZoneEntity.getZoneId());
//        assertEquals("test_city",getZoneEntity.getCity());
//        assertEquals(1,getZoneEntity.getCordX());
//        assertEquals(1,getZoneEntity.getCordY());
//        assertEquals(0.5,getZoneEntity.getOccupiedRatio());
//        assertEquals(0.5,getZoneEntity.getAttractivenessRatio());
//    }
//
//    @Test
//    void getZoneNoSuchZoneExceptionTest(){
//        when(zoneRepository.findById(zoneId)).thenReturn(Optional.empty());
//        assertThrows(NoSuchZoneException.class, () -> zoneService.getZone(zoneId));
//    }
//
//    @Test
//    void getAllZones(){
//        when(zoneRepository.findAll()).thenReturn(zonesEntities);
//        List<ZoneEntity> zonesEntityList = this.zoneService.getAllZones();
//        assertEquals(zonesEntity,zonesEntityList.get(0));
//        assertEquals(zonesEntity1,zonesEntityList.get(1));
//    }
//    @Test
//    void addZoneTest(){
//        when(zoneRepository.save(any(ZoneEntity.class))).thenReturn(zonesEntity1);
//        ZoneEntity newZoneEntity = this.zoneService.addZone(zonesEntity1);
//        assertNotNull(newZoneEntity);
//        assertEquals(1, newZoneEntity.getZoneId());
//        assertEquals("new_city",newZoneEntity.getCity());
//        assertEquals(2,newZoneEntity.getCordX());
//        assertEquals(2,newZoneEntity.getCordY());
//        assertEquals(0.3,newZoneEntity.getOccupiedRatio());
//        assertEquals(0.3,newZoneEntity.getAttractivenessRatio());
//    }
//
//    @Test
//    void deleteZoneTest() {
//        Optional<ZoneEntity> optionalZoneEntity = Optional.of(zonesEntity);
//
//        when(zoneRepository.findById(zoneId)).thenReturn(optionalZoneEntity);
//
//        zoneService.deleteZone(zoneId);
//
//        Mockito.verify(zoneRepository, times(1)).delete(optionalZoneEntity.get());
//    }
//
//    @Test
//    void deleteZoneNoSuchZoneExceptionTest(){
//        when(zoneRepository.findById(zoneId)).thenReturn(Optional.empty());
//        assertThrows(NoSuchZoneException.class, () -> zoneService.deleteZone(zoneId));
//    }
//
//
//    @Test
//    void  changeOccupiedRatioTest(){
//        when(zoneRepository.findById(2L)).thenReturn(Optional.of(zonesEntity2));
//        when(zoneRepository.save(any(ZoneEntity.class))).thenReturn(zonesEntity3);
//        this.zoneService.changeZoneOccupiedRatio(2L,1.0);
//        assertEquals(1.0,zonesEntity3.getOccupiedRatio());
//    }
//
//    @Test
//    void  changeAttractivenessRatioTest(){
//        when(zoneRepository.findById(2L)).thenReturn(Optional.of(zonesEntity2));
//        when(zoneRepository.save(any(ZoneEntity.class))).thenReturn(zonesEntity3);
//        this.zoneService.changeZoneAttractivenessRatio(2L,1.0);
//        assertEquals(1.0,zonesEntity3.getAttractivenessRatio());
//    }
//}
