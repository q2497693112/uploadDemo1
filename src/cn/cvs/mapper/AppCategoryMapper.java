package cn.cvs.mapper;

import cn.cvs.pojo.AppCategory;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface AppCategoryMapper {
    List<AppCategory> appCategoryList(@Param("parentId") Integer parentId);
}
