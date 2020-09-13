set rc=java -jar ../target/ctclient.jar

REM no args should show usage
%rc%
PAUSE

REM Ensure CabTripServer has started
PAUSE

REM Clear Server Cache
%rc% -clearcache
PAUSE

REM Run with cache
%rc% -csvfile in.csv
PAUSE

REM  Run without cache
%rc% -csvfile in.csv -nocache
PAUSE

REM  Run with bad csvfile
%rc% -csvfile bad.csv -nocache
PAUSE

REM  Run with csvfile not found
%rc% -csvfile blah.csv -nocache
PAUSE

REM show usage
%rc% -clearcache -csvfile in.csv
PAUSE

REM show usage
%rc% -csvfile
PAUSE
