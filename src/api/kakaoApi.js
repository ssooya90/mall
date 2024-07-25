

const rest_api_key = '883969be5c1bc3be6c0323bd014734a6'
const redirect_uri = 'http://localhost:3000/member/kakao'

const auth_code_path = `https://kauth.kakao.com/oauth/authorize`

export const getKakaoLoginLink = () =>{


	const kakaoUrl = `${auth_code_path}?client_id=${rest_api_key}&redirect_uri=${redirect_uri}&response_type=code`

	return kakaoUrl
}
