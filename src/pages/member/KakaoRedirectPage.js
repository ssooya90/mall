import React, {useEffect} from 'react';
import {useSearchParams} from "react-router-dom";
import {getAccessToken} from "../../api/kakaoApi";

function KakaoRedirectPage(props) {

	const [searchParams] = useSearchParams()
	const authCode = searchParams.get('code')

	useEffect(() => {
		getAccessToken(authCode).then(data => {
			console.log(data)
		})

	}, [authCode]);

	return (
			<div>
				<div>Kakao Login Redirect</div>
				<div>${authCode}</div>
			</div>
	);
}

export default KakaoRedirectPage;