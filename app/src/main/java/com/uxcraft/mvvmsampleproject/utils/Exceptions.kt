package com.uxcraft.mvvmsampleproject.utils

import java.io.IOException

//taking the message from API EXCEPTION to the IOException

class ApiException(message: String) :IOException(message)


class NoInternetException(message: String) :IOException(message)