package com.safedog.cloudnet.generate.utils;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

/**
 * 除外不出现在实体里
 */
public class FieldsExclude {
	public static final Set<String> FIELDS_EXCLUDE = ImmutableSet.of("created_by_user", "created_date",
            "updated_by_user", "updated_date", "created_app_id",
			"updated_app_id", "created_notary_office","record_version","is_deleted");
	public static final Set<String> CLOUD_EXCLUDE = ImmutableSet.of(
			"id",
			"created_by_user", "created_date", "created_app_id","created_notary_office_id",
			"updated_by_user", "updated_date","updated_app_id","deleted",
			"deleted_by_user", "deleted_date","log_record", "record_version");
}
