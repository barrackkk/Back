import com.fitpet.server.dailyworkout.domain.entity.GpsLog;
import com.fitpet.server.dailyworkout.domain.entity.GpsSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring") // 스프링 빈으로 등록
public interface GpsLogMapper {

    GpsLogMapper INSTANCE = Mappers.getMapper(GpsLogMapper.class);

    /**
     * GpsLogRequestDto와 GpsSession 엔티티를 GpsLog 엔티티로 변환합니다.
     *
     * @param dto        클라이언트로부터 받은 요청 DTO
     * @param gpsSession 서비스 레이어에서 조회한 GpsSession 엔티티
     * @return 생성된 GpsLog 엔티티
     */
    @Mapping(source = "gpsSession", target = "gpsSession") // 2. 파라미터로 받은 gpsSession을 target의 gpsSession에 매핑
    @Mapping(target = "id", ignore = true)                // 3. id 필드는 무시 (자동 생성)
    @Mapping(target = "createdAt", ignore = true)
    // 4. createdAt 필드도 무시 (자동 생성)
    GpsLog toEntity(GpsLogRequestDto dto, GpsSession gpsSession); // 1. dto 외에 GpsSession을 파라미터로 받음
}
