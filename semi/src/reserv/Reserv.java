package reserv;

import java.util.List;

import vo.AirInfoVO;
import vo.ReservVO;

public interface Reserv {
	public void reserv(List<ReservVO> list_reserv, List<AirInfoVO> list_info, String id); // 예매
	public void cancleReserv(List<ReservVO> list_reserv, List<AirInfoVO> list_info, String id); // 예매취소
	public ReservVO readReservNO(ReservVO vo, List<ReservVO> list_reserv); // 예매번호 대조
	
}
