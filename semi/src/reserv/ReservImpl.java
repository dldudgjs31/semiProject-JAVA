package reserv;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import vo.AirInfoVO;
import vo.ReservVO;

public class ReservImpl implements Reserv {
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private Scanner sc = new Scanner(System.in);

	/*
	 * 예매기능
	 */
	@Override
	public void reserv(List<ReservVO> list_reserv, List<AirInfoVO> list_info, String id) {
		ReservVO re = new ReservVO();
		AirInfoVO in = null;
		String[][] test = new String[list_info.size()][4];
		int cnt = 0;
		int select;

		try {
			if (list_info.size() == 0) {
				System.out.println("항공정보가 없습니다.\n");
				return;
			}

			List<AirInfoVO> result = new LinkedList<AirInfoVO>();
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String today = sdf.format(date);
			
			
			// 운항정보 오름차순 출력
			for (AirInfoVO vo : list_info) {
				if (vo.getS_Day().compareTo(today) >= 0) {
					result.add(vo);
				}
			}
			
			// 운항정보가 없을 경우
			if (result.size() == 0) {
				System.out.println("항공정보가 없습니다.");
				return;
			}

			System.out.println("\n[운항목록]\n");
			System.out.print("번호\t항공기명\t출발지\t도착지\t출발시간\t\t\t도착시간\t\t\t남은좌석\n");
			// 운항정보 출력
			for (AirInfoVO vo : result) {
				System.out.print("(" + (cnt + 1) + ")\t" + vo.getA_Name() + "\t");
				test[cnt][0] = vo.getA_Name();
				System.out.print(vo.getDeparture() + "\t");
				test[cnt][1] = vo.getDeparture();
				System.out.print(vo.getArrival() + "\t");
				test[cnt][2] = vo.getArrival();
				System.out.print(vo.getS_Day() + "\t");
				test[cnt][3] = vo.getS_Day();
				System.out.print(vo.getE_Day() + "\t");
				System.out.print(vo.getSeat_cnt() + "\n");

				cnt++;
			}

			do {
				System.out.print("원하시는 항공기의 번호를 입력해주세요.");
				select = sc.nextInt();
			} while (select < 1 || select > list_info.size());

			int sel = select - 1;

			re.setA_Name(test[sel][0]);
			re.setDeparture(test[sel][1]);
			re.setArrival(test[sel][2]);
			re.setS_Day(test[sel][3]);
			
			// 출발지 도착지 출발시간 항공기명이 운항정보에 등록된 것과 일치하지 않으면 예매되지않음.
			for (AirInfoVO ai : list_info) {
				if (re.getDeparture().equals(ai.getDeparture()) && re.getArrival().equals(ai.getArrival())
						&& re.getS_Day().equals(ai.getS_Day()) && re.getA_Name().equals(ai.getA_Name())) {
					if (ai.getSeat_cnt() <= 0) {
						System.out.println("좌석없음");
						return;
					}

					in = ai;
					break;
				}
			}
			
			if (in == null) {
				System.out.println("잘못된 입력입니다.\n");
				return;
			}
			
			// 예매번호 생성
			String reserv = "20-00000";
			if (list_reserv.size() > 0) {
				reserv = list_reserv.get(list_reserv.size() - 1).getReservNo();
			}

			String reserv1 = reserv.substring(0, reserv.lastIndexOf("-"));
			String reserv2 = reserv.substring(reserv.lastIndexOf("-") + 1);

			int n = Integer.parseInt(reserv2) + 1;

			reserv = reserv1 + "-" + String.format("%05d", n);

			// id값이 없을 경우 타인의 예매정보를 예매번호만 알고있으면 삭제 할수 있기 때문에
			re.setId(id); // 추후의 예매취소시 대조할 id값이 있어야 하기 때문에 예매정보 객체에 회원정보 id를 넣어줌
			re.setReservNo(reserv); // 생성된 예매번호를 예매정보 객체에 넣어줌
			System.out.println("\n예매되었습니다.");
			System.out.println("예매번호 : " + reserv); 

			in.setSeat_cnt(in.getSeat_cnt() - 1); // 예매 성공시 기존 항공정보에 저장된 좌석수-1
			list_reserv.add(re); // 예매정보 리스트에 저장
			return;

		} catch (InputMismatchException e) {
			sc.nextLine();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

	}

	/*
	 * 예매취소
	 */
	@Override
	public void cancleReserv(List<ReservVO> list_reserv, List<AirInfoVO> list_info, String id) {

		try {
			ReservVO vo = new ReservVO();

			System.out.println("예매를 취소할 번호를 입력해주세요.");
			vo.setReservNo(br.readLine());

			ReservVO de = readReservNO(vo, list_reserv); // 예매번호 대조 메소드를 불러와서 그 결과 값을 de에 넣어줌

			if (de == null) { // de가 리스트에 없으면
				System.out.println("존재하지 않는 예매번호입니다.\n");
				return;
			}
			
			
			// 예매정보에 저장된 id값과 회원정보의 id값이 다를 시 예매번호가 맞더라도 삭제 할 수 없음
			if (de.getId().equals(id)) {
				list_reserv.remove(de); // 해당 객체 삭제
				System.out.println("예매가 취소되었습니다.\n");
				
				for (AirInfoVO iv : list_info) {
					// 삭제된 예매정보의 출발지, 도착지, 출발날자가 운항정보에 존재할 경우  
					if (de.getDeparture().equals(iv.getDeparture()) && de.getArrival().equals(iv.getArrival())
							&& de.getS_Day().equals(iv.getS_Day())) {
						// 좌석수를 +1해줌
						iv.setSeat_cnt(iv.getSeat_cnt() + 1);
						break;
					}
				}

			} else {
				System.out.println("예매정보가 일치하지 않습니다.\n");
			}
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * 예매번호 대조
	 */
	@Override
	public ReservVO readReservNO(ReservVO vo, List<ReservVO> list_reserv) {
		for (ReservVO re : list_reserv) {
			if (re.getReservNo().equals(vo.getReservNo())) {
				return re;
			}
		}
		return null;
	}
}
