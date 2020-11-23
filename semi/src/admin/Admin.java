package admin;

import java.util.List;

import vo.AirInfoVO;
import vo.ReservVO;
import vo.UserVO;

public interface Admin {
//	항공기 목록 조회
	public void airList(List<AirInfoVO> list_info);

//	항공기 저장
	public void addAirList(List<AirInfoVO> list_info);

//	항공정보삭제
	public void deleteAirList(List<AirInfoVO> list_info, List<ReservVO> list_reserv);

//	유저 관리
	public void userControl(List<UserVO> list_user);

//	항공정보 중복확인
	public AirInfoVO readInfo(AirInfoVO vo, List<AirInfoVO> list_info);
}
