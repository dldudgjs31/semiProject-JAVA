package util;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

import vo.AirInfoVO;
import vo.ReservVO;
import vo.UserVO;


/**
 * 예약정보, 항공정보,회원정보 등록/수정/삭제시 파일을 업데이트하고, 
 * 불러오는 기능이 담긴 클래스입니다.
 * 해당 정보는 txt파일로 저장됩니다.
 * @author 이영헌
 *
 */
public class FileUtil {
	private List<UserVO> list_user = new LinkedList<>();
	private List<AirInfoVO> list_info = new LinkedList<>();
	private List<ReservVO> list_reserv = new LinkedList<>();

	private String User_Path = "User.txt"; // 유저정보 파일
	private String AirInfo_Path = "AirInfo.txt"; // 항공정보 파일
	private String Reserv_Path = "Reserv.txt"; // 예약정보 파일

	// 생성자
	public FileUtil() {
		load_UserF();
		load_AirInfoF();
		load_ReservF();
	}

	/*
	 * 유저 정보 파일처리 메소드
	 */

	public void save_UserF() {
		File file = new File(User_Path);

		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(file));
			for (UserVO vo : list_user) {
				oos.writeObject(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (Exception e2) {
				}
			}
		}

	}

	/*
	 * 유저 정보 파일 로드
	 */
	public void load_UserF() {
		File file = new File(User_Path);
		if (!file.exists()) {
			return;
		}
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(file));

			while (true) {
				UserVO vo = (UserVO) ois.readObject();
				list_user.add(vo);
			}
		} catch (EOFException e) {
			// 아무것도 입력해선 안됨
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (Exception e2) {
				}
			}
		}
	}

	/*
	 * 항공정보 파일처리
	 */
	public void save_AirInfoF() {
		File file = new File(AirInfo_Path);

		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(file));
			for (AirInfoVO vo : list_info) {
				oos.writeObject(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (Exception e2) {
				}
			}
		}
	}

	/*
	 * 항공정보 파일 로드
	 */
	public void load_AirInfoF() {
		File file = new File(AirInfo_Path);
		if (!file.exists()) {
			return;
		}
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(file));

			while (true) {
				AirInfoVO vo = (AirInfoVO) ois.readObject();
				list_info.add(vo);
			}
		} catch (EOFException e) {
			// 아무것도 입력해선 안됨
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (Exception e2) {
				}
			}
		}
	}

	/*
	 * 예약정보 파일처리
	 */
	public void save_ReservF() {
		File file = new File(Reserv_Path);

		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(file));
			for (ReservVO vo : list_reserv) {
				oos.writeObject(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (Exception e2) {
				}
			}
		}
	}

	/*
	 * 예약정보 파일 로드
	 */
	public void load_ReservF() {
		File file = new File(Reserv_Path);
		if (!file.exists()) {
			return;
		}
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(file));

			while (true) {
				ReservVO vo = (ReservVO) ois.readObject();
				list_reserv.add(vo);
			}
		} catch (EOFException e) {
			// 아무것도 입력해선 안됨
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (Exception e2) {
				}
			}
		}
	}

	/*
	 * 파일에 있는 정보를 list로 돌려 받는 메소드.
	 */

	public List<UserVO> getUser() {
		return list_user;
	}

	public List<AirInfoVO> getInfo() {
		return list_info;
	}

	public List<ReservVO> getReserv() {
		return list_reserv;
	}
}
