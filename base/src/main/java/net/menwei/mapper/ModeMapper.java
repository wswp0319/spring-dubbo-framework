package net.menwei.mapper;

import net.menwei.domain.ModeDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * ModeMapper数据库操作接口类
 * 
 **/
@Mapper
public interface ModeMapper<T extends ModeDto> extends IMapper<T> {

}