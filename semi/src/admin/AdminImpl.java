package admin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import user.User;
import user.UserImpl;
import util.DateUtil;
import vo.AirInfoVO;
import vo.ReservVO;
import vo.UserVO;

public class AdminImpl implements Admin {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	DateUtil du = new DateUtil();
	private Scanner sc = new Scanner(System.in);

	@Override
	public AirInfoVO readInfo(AirInfoVO addVO, List<AirInfoVO> list_info) {
		for (AirInfoVO vo : list_info) {
			if (vo.getA_Name().equals(addVO.getA_Name()) && vo.getS_Day().equals(addVO.getS_Day())) { //항공이름이랑 출발날짜 같으면
				return vo; //항공정보객체 리턴
			}
		}
		return null; //항공이름이랑 출발날짜 다르면 null리턴
	}
	

	@Override
	public void airList(List<AirInfoVO> list_info) {
		if (list_info.size() == 0) { //리스트에 아무정보도 없으면 -> 리스트 사이즈 0
			System.out.println("항공정보가 없습니다.\n");
			return;
		}

		System.out.print("항공기명\t출발지\t도착지\t출발날짜\t\t\t도착날짜\t\t\t남은좌석\n");
		for (AirInfoVO vo : list_info) { //향상된 for문으로 리스트 전체 내용 출력
			System.out.print(vo.getA_Name() + "\t");
			System.out.print(vo.getDeparture() + "\t");
			System.out.print(vo.getArrival() + "\t");
			System.out.print(vo.getS_Day() + "\t");
			System.out.print(vo.getE_Day() + "\t");
			System.out.print(vo.getSeat_cnt() + "\n");
		}

	}

	@Override
	public void addAirList(List<AirInfoVO> list_info) {
		try {
			Date date = new Date(); //Date객체 생성
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String today = sdf.format(date); //yyyy-MM-dd HH:mm형태로 오늘 날짜 date객체를 string으로 변형


			AirInfoVO vo = new AirInfoVO(); //항공정보 객체 생성

			System.out.print("항공기명 ? ");
			vo.setA_Name(br.readLine()); //한줄씩 입력하면서 정보를 항공정보 객체에 넣어준다.
			System.out.print("출발지 ? ");
			vo.setDeparture(br.readLine());
			System.out.print("도착지 ? ");
			vo.setArrival(br.readLine());

			
			
			System.out.print("출발날짜 ? ");
			String arrivalday = br.readLine(); //출발날짜 string을 입력받아서
			
			String change_arrivday = du.standardDate(arrivalday); //다양한 형태의 출발날짜 string형태를  yyyy-MM-dd HH:mm로 동일하게 변형시킴
			
			if (du.isValidDate(arrivalday) == true) { //입력받은 날짜스트링이 유효하고
				if(change_arrivday.compareTo(today)>=0) { // 출발날짜와 오늘날짜를 비교했을때, 오늘날짜보다 출발날짜가 이후이면
					vo.setS_Day(change_arrivday); //입력받은 날짜를 항공정보 객체에 넣음
				}
				else { //오늘날짜보다 출발날짜가 이전이면 잘못된 항공정보이므로 리턴
					System.out.println("잘못된 날짜입니다.");
					return;
				}
			} else { //날짜 스트링이 유효하지 않은 값이면 리턴
				System.out.println("잘못된 날짜입니다.");
				return;
			}

			
			
			System.out.print("도착날짜 ? "); 
			String departureday = br.readLine();                     //도착날짜 string 입력
			
			String change_departday = du.standardDate(departureday); //도착날짜도 출발날짜와 마찬가지로 yyyy-MM-dd HH:mm형태로 동일하게 변형시킴
			
			if (du.isValidDate(departureday) == true) {             //입력받은 도착날짜가 유효하고,
				
				if(change_departday.compareTo(change_arrivday)>=0) //도착날짜보다 출발날짜가 이전이면,  (출발날짜 - 도착날짜 순)
					vo.setE_Day(change_departday);                 //항공정보 객체에 정보저장
				
				else {                                              //도착날짜보다 출발날짜가 이후이면,  (도착날짜 - 출발날짜 순) 리턴
					System.out.println("잘못된 날짜입니다.");
					return;
				}					
			} else {                                               //도착날짜가 유효하지 않은 값이면 리턴
				System.out.println("잘못된 날짜입니다.");
				return;
			}

			
			
			System.out.print("남은좌석 ? ");
			vo.setSeat_cnt(Integer.parseInt(br.readLine()));      // 비행기의 좌석 갯수 입력받고 저장.

			AirInfoVO test = readInfo(vo, list_info);             //새로 입력받는 항공정보 객체와 Airinfo.txt에 저장된 리스트에서 중복이 있는지 검사

			
			if (test != null) {                                    // 입력받은 AirInfoVO객체가 air리스트에 있으면 => "중복된 항공정보입니다." 출력
				System.out.println("중복된 항공정보입니다.\n");
				return;
			}

			list_info.add(vo);                                     // 중복된 객체가 없다면 air에 해당 입력받은 객체 정보 넘겨줌.
			System.out.println("항공정보가 등록되었습니다.");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	@Override
	public void deleteAirList(List<AirInfoVO> list_info, List<ReservVO> list_reserv) {
		try {
			AirInfoVO vo = new AirInfoVO();
			String[][] test = new String[list_info.size()][6];     //test라는 String 2차원 배열 생성 (행은 리스트의 갯수/ 열은 항공기명....도착시간,남은좌석 = 6가지)
			int cnt = 0;
			int select;

			System.out.println("\n[운항목록]\n");
			System.out.print("번호\t항공기명\t출발지\t도착지\t출발시간\t\t\t도착시간\t\t\t남은좌석\n");
			for (AirInfoVO vi : list_info) {                                      // Airinfo.txt에 저장된 리스트 값을 [(번호) 항공기명 출발지 도착지 출발시간 도착시간 남은좌석]의 형태로 출력
				                                                                  // txt파일의 정보를 test이차원 배열에 다시 저장.
				System.out.print("(" + (cnt + 1) + ")\t" + vi.getA_Name() + "\t");
				test[cnt][0] = vi.getA_Name();
				System.out.print(vi.getDeparture() + "\t");
				test[cnt][1] = vi.getDeparture();
				System.out.print(vi.getArrival() + "\t");
				test[cnt][2] = vi.getArrival();
				System.out.print(vi.getS_Day() + "\t");
				test[cnt][3] = vi.getS_Day();
				System.out.print(vi.getE_Day() + "\t");
				System.out.print(vi.getSeat_cnt() + "\n");

				cnt++;                            //운항 목록에서 번호를 출력하기 위해 
			}

			do {
				System.out.print("원하시는 항공기의 번호를 입력해주세요."); //위에 생성된 리스트 목록에서 원하는 번호를 선택하면
				select = sc.nextInt(); 
			} while (select < 1 || select > list_info.size());

			int sel = select - 1;                                      //리스트는 0번부터 시작이니까 인덱스 값을 선택한번호 -1 로 지정

			vo.setA_Name(test[sel][0]);                                //선택한 번호의 리스트 정보를  vo항공정보객체에 다시 저장 
			vo.setDeparture(test[sel][1]);
			vo.setArrival(test[sel][2]);
			vo.setS_Day(test[sel][3]);

			AirInfoVO result = readInfo(vo, list_info); //선택한 객체와 리스트에 같은 객체가 있는지 확인, 같은 객체가 있으면 객체 리턴받고, 없으면 null 리턴받음

			if (result == null) {                       // 객체가 리스트에 저장되어있지 않으면
				System.out.println("해당 정보가 없습니다.\n");
				return;                                 // "해당 정보가 없습니다"
			}

			for (ReservVO vf : list_reserv) {         
				if (vf.getA_Name().equals(vo.getA_Name())) {
					list_reserv.remove(vf);
				}
			}

			list_info.remove(result); // 해당 객체 삭제
			System.out.print("해당정보가 삭제되었습니다.\n");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	
	@Override
	public void userControl(List<UserVO> list_user) {
		List<UserVO> result = new LinkedList<UserVO>();
		int cnt = 0;
		char select;

		for (UserVO vo : list_user) {         //유저 리스트를 탐색
			if (!vo.getId().equals("user")) { //"user"를 제외한 모든 사용자를 새로 생성한 result 리스트에 유저 추가
				result.add(vo);               
			}
		}

		if (result.size() == 0) {      //유저 리스트 사이즈가 0이면 저장된 정보가 없음
			System.out.println("저장된 데이터가 없습니다. \n");
			return;
		}

		
		// User.txt에 저장된 유저들의 정보를 [(번호) 이름 아이디 패스워드 생년월일]의 형태로 출력
		// 동시에 유저 정보를 새로운 string 이차원 배열에 저장
		String[][] userInfo = new String[result.size()][4]; 

		System.out.print("번호\t이름\t아이디\t패스워드\t생년월일\n");
		for (UserVO vo : result) {
			System.out.print("(" + (cnt + 1) + ")\t" + vo.getName() + "\t");
			userInfo[cnt][0] = vo.getName();
			System.out.print(vo.getId() + "\t");
			userInfo[cnt][1] = vo.getId();
			System.out.print(vo.getPw() + "\t");
			userInfo[cnt][2] = vo.getPw();
			System.out.print(vo.getBirth() + "\n");
			userInfo[cnt][3] = vo.getBirth();
			cnt++;
		}

		
		
		try {
			do {
				System.out.print("[1]계정삭제  [2]계정정보변경  [3]종료");
				select = br.readLine().charAt(0); 
			} while (select < '1' || select > '3');

			if (select == '3') {
//				종료
				return;
			}

			switch (select) {
			case '1':
//				계정삭제
				int select_delete = 0;
				try {
					do {
						
						System.out.print("삭제할 번호"); //위의 유저 리스트에서 삭제할 번호를 입력받음
						select_delete = sc.nextInt();
						if(select_delete > result.size() || select_delete <1) {
							System.out.println("해당하는 번호가 없습니다.");
							return;
						}
					} while (select_delete < 1 || select_delete > result.size()); 
				

				} catch (InputMismatchException e) {
					sc.nextLine();
					return;
				} catch (Exception e) {
					e.printStackTrace();
				}

				select_delete--;  //선택한 번호를 -1 했을때,
				
				if (select_delete == -1) { //-1이면 오류 (번호는 1부터시작 )
					System.out.println("오류");
					return;
				}

				

				for (UserVO vo : list_user) {
					if (vo.getName().equals(userInfo[select_delete][0]) && vo.getId().equals(userInfo[select_delete][1]) //유저 리스트에서 삭제할 유저의 이름과, 패스워드 생일이 같은 유저가 있으면
							&& vo.getPw().equals(userInfo[select_delete][2])
							&& vo.getBirth().equals(userInfo[select_delete][3])) {
						list_user.remove(vo);                                                           //삭제
						System.out.println("\n아이디가 삭제되었습니다.");
						break;
					}
				}

				break;
			case '2':
//				계정정보변경
				User us = new UserImpl();
				int select_update = 0;

				try {
					do {
						System.out.print("수정할 번호");
						select_update = sc.nextInt();
						if(select_update > result.size() || select_update <1) {
							System.out.println("해당하는 번호가 없습니다.");
							return;
						}
					} while (select_update < 1 || select_update > result.size());

				} catch (InputMismatchException e) {
					//sc.nextLine();
					return;
				} catch (Exception e) {
					e.printStackTrace();
				}

				select_update--;

				if (select_update == -1) {
					System.out.println("오류");
					return;
				}

				for (UserVO vo : list_user) {
					if (vo.getName().equals(userInfo[select_update][0]) && vo.getId().equals(userInfo[select_update][1]) //유저 리스트에서 삭제할 유저의 이름과,아이디,패스워드,생일이 같은 유저가 있으면
							&& vo.getPw().equals(userInfo[select_update][2])
							&& vo.getBirth().equals(userInfo[select_update][3])) {
						us.updateUser(vo, list_user);                           //리스트에 해당 객체의 정보를 업데이트
						break;
					}
				}
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
