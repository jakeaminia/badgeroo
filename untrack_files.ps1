# Get a list of all tracked files
$trackedFiles = git ls-files

# Read each line from the .gitignore
Get-Content .gitignore | ForEach-Object {
    $pattern = $_.Trim()

    # Skip comment lines or empty lines
    if ($pattern.StartsWith("#") -or !$pattern) {
        return
    }

    # For directory patterns ending with /, adjust for PowerShell's -like
    if ($pattern.EndsWith("/")) {
        $pattern = "$pattern*"
    }

    # Match the pattern against tracked files and untrack matches
    $trackedFiles | Where-Object { $_ -like $pattern } | ForEach-Object {
        git update-index --assume-unchanged $_
    }
}