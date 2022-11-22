import { createSlice } from "@reduxjs/toolkit";

const counterReducer = createSlice({
  name: 'counter',
  initialState: { value: 0 },
  reducers: {
    add: (state, action) => {
      state.value += action.payload || 1;
    }
  }
});

export const { add } = counterReducer.actions;

export default counterReducer.reducer;
