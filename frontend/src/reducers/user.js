import { createSlice } from "@reduxjs/toolkit";
import { getUserType } from "../services/token";

const initialState = {
  role: null
};
const user = createSlice({
  name: 'user',
  initialState,
  reducers: {
    setRole: (state) => {
      state.role = getUserType();
    }
  }
});

export const { setRole } = user.actions;

export default user.reducer;
