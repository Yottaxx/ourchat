package bit.group.ourchat.repository;

import bit.group.ourchat.entity.GroupMembers_new;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMembersRepository extends JpaRepository<GroupMembers_new,Integer> {

    @Query(value = "select m from GroupMembers_new m")
    public List<GroupMembers_new> findAll();

    @Query(value = "select m from GroupMembers_new m where m.group_chat_id=?1")
    public List<GroupMembers_new> findGroupById(Integer id);

//    @Modifying
//    @Transactional
//    @Query(value = "delete parcel,parcel_file,ms_files,t_order,route " +
//            "from parcel left join route on parcel.route_id = route.id" +
//            " left join t_order on t_order.parcel_id = parcel.id" +
//            " left join parcel_file on parcel.id = parcel_file.parcel_id" +
//            " left join ms_files on parcel_file.file_uuid = ms_files.uuid " +
//            "where parcel.id = ?1", nativeQuery = true)
//    void deleteByParcelId(Long parcelId);
}
