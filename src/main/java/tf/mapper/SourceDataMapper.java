package tf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tf.entity.SourceData;

import java.util.List;
@Mapper
public interface SourceDataMapper  extends BaseMapper<SourceData> {
    List<SourceData> selectTop10();
}
