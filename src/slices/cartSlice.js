

// 1. 비동기 처리

import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import {getCartItems, postChangeCart} from "../api/cartApi";


// extra reducer로 동작 시키기 위해서 사용
export const getCartItemsAsync = createAsyncThunk('getCartItemsAsync', () => {

	return getCartItems();
})


export const postChangeCartAsync = createAsyncThunk('postChangeCartAsync', (param) => {

	return postChangeCart(param);
})

const initState = []

const cartSlice = createSlice({

	name:'cartSlice',
	initialState:initState,

	extraReducers : (builder) => {

		builder
				.addCase(getCartItemsAsync.fulfilled, (state, action) => {

					return action.payload
				})

				.addCase(postChangeCartAsync.fulfilled, (state, action) => {

					// action.payload가 실제 데이터
					return action.payload

				})
	}

})



// 리듀서를 반환해야 함
export default cartSlice.reducer